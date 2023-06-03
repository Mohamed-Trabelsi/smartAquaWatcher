const mqtt = require('mqtt');
const mysql = require('mysql2');
const fs = require('fs');
const schedule = require('node-schedule');
const client = mqtt.connect("mqtt://broker.emqx.io");
const topicTemp='water/temperature';
const topicPh = 'water/ph';
const topicHumid = 'water/humidity';
const topicTurb = 'water/turbidity';
const topicAdress = 'macAdress/esp32';
let temp,ph,humid,turb,adress;

function getMacAdress()
{
const os = require('os');

const networkInterfaces = os.networkInterfaces();
const macAddress = networkInterfaces['wlan0'][0].mac;

return macAddress;
}

function hash(input) {
  const crypto = require('crypto');

  const hash = crypto.createHash('sha256');
  hash.update(input);

  return hash.digest('hex');
}

function readTextFile(filePath) {
  try {
    const fileData = fs.readFileSync(filePath, 'utf-8');
    return fileData;
  } catch (err) {
    console.error(err);
  }
}

function connectToDatabase(filename) {
  const credentials = fs.readFileSync(filename, 'utf8').split('\n');

  const [host, user, password, database] = credentials.map(c => c.trim());
  const connection = mysql.createConnection({
    host:  host,
    user: user,
    password: password,
    database: database
  });

  connection.connect((err) => {
    if (err) throw err;
    console.log('Connected to database');
  });

  return connection;
}

function subscribeToTopic(topic) {
  client.subscribe(topic, (err, granted) => {
    if(err) {
      console.error(`Error subscribing to topic "${topic}" - ${err}`);

    }
   console.log(`Successfully subscribed to topic "${topic}"`);
  });
}
client.on('connect', () => {
  subscribeToTopic(topicTemp);
  subscribeToTopic(topicPh);
  subscribeToTopic(topicHumid);
  subscribeToTopic(topicTurb);
  subscribeToTopic(topicAdress);
});

let macaddress = hash(getMacAdress());
createBarrage(macaddress);

const updateJob = schedule.scheduleJob('0 0 * * *', () => {
  // This will run the updateColumn() function every day at midnight
  updateColumn();
});

client.on('error', (err) => {
  console.error(`Error in MQTT connection - ${err}`);
});

client.on('disconnect', (reasonCode) => {
  console.error(`Disconnected from MQTT broker - reason code: ${reasonCode}`);
});

client.on('message', (topic, message, packet) => {
  console.log("----------");

  if(topic === topicTemp) {
try{
    temp = JSON.parse(message).toString();
    console.log("Temperature = "+temp);
}catch (err) {
      console.error(`Error parsing temperature - ${err}`);
    }
  }
  if(topic === topicPh) {
try{
    ph = JSON.parse(message).toString();
    console.log("PH = "+ph);
}catch (err) {
      console.error(`Error parsing PH - ${err}`);
    }
  }

  if(topic === topicHumid) {
try{
    humid = JSON.parse(message).toString();
    console.log("Humidite = "+humid);
}catch (err) {
      console.error(`Error parsing humidity - ${err}`);
    }
  }
  if(topic === topicTurb) {
try{
    turb = JSON.parse(message).toString();
     console.log("Turbidite =  "+turb);
}catch (err) {
      console.error(`Error parsing turbidity - ${err}`);
    }
  }
  if(topic === topicAdress) {
try{
    adress = message.toString();
    console.log("Adresse mac = "+adress);
}catch (err) {
      console.error(`Error parsing Adress - ${err}`);
    }
  }

if (temp && ph && humid && turb && adress){
  addNoeud(adress);
  insertDataIntoDB();
}
});

function insertDataIntoDB()
{
//const con = connectToDatabase('config.txt');
const con = mysql.createConnection({
  host: "192.168.1.199",
  user: "root",
  password: "",
  database: "pi4sleam"
});
con.connect(function(err) {

  if (err) {
  //console.error('Error connecting to database: ' + err.stack);
 insertDataIntoLocalDB();
return;
  }
else{
console.log('Connected to database as id ' + con.threadId);
if ((ph>6.5)&&(ph<9.5))
{
  var sql = "INSERT INTO grandeur (critique, humidite, ph, temp, turbidite, noeud_id_noeud) VALUES (0,"+humid+","+ph+","+temp+","+turb+",1)";
}
else
{
var sql = "INSERT INTO grandeur (critique, humidite, ph, temp, turbidite, noeud_id_noeud) VALUES (1,"+humid+","+ph+","+temp+","+turb+",1)";
}
//try{
con.query(sql, function (err, result) {
    //if (err) throw(err);
    console.log("1 record inserted");
temp = ph = humid = turb = adress = null;

  });
/*}
catch{
insertDataIntoLocalDB();
}*/
}
});
}

function insertDataIntoLocalDB()
{
const conn = mysql.createConnection({
  host: "127.0.0.1",
  user: "med",
  password: "med",
  database: "waterquality"
});

conn.connect(function(err) {
  if (err) {
    console.error('Error connecting to database: ' + err.stack);
  }
console.log('Connected to local  database');
if(adress == "30:C6:F7:15:0A:78")
{
if ((ph>6.5)&&(ph<9.5))
{
  var sql = "INSERT INTO grandeur (temp, humidite, ph, turbidite, critique, noeud) VALUES ("+temp+","+humid+","+ph+","+turb+",0,1)";
}
else
{
var sql = "INSERT INTO grandeur (temp, humidite, ph, turbidite, critique, noeud) VALUES ("+temp+","+humid+","+ph+","+turb+",1,1)";
}
}
else
{
if ((ph>6.5)&&(ph<9.5))
{
var sql = "INSERT INTO grandeur (temp, humidite, ph, turbidite, critique, noeud) VALUES ("+temp+","+humid+","+ph+","+turb+",0,2)";
}
else
{
var sql = "INSERT INTO grandeur (temp, humidite, ph, turbidite, critique, noeud) VALUES ("+temp+","+humid+","+ph+","+turb+",1,2)";
}
}
conn.query(sql, function (err, result) {
    if (err){ //throw err;
return;}
    console.log("1 record inserted");
  });
temp = ph = humid = turb = adress = null;
});

}

function createBarrage(macaddress) {
const con = mysql.createConnection({
  host: "192.168.1.199",
  user: "root",
  password: "",
  database: "pi4sleam"
});
try{
  con.connect(function(err) {
    if (err) {
      //console.error('Error connecting to database: ' + err.stack);
return;
    }
    console.log('Connected to database');
    var sqlSelect = `SELECT COUNT(*) AS count FROM barage WHERE mac='${macaddress}'`;
    con.query(sqlSelect, function (err, result) {
      if (err) throw err;
      if (result[0].count > 0) {
        console.log(`Barrage already exists.`);
      } else {
var filePath = "../data.txt";
 var niveau = readTextFile(filePath);
        var sqlInsert = `INSERT INTO barage (capacite, mac, niveau, nom, region) VALUES (50,'${macaddress}' ,'${niveau}', 'sidi barrak', 'beja')`;
        con.query(sqlInsert, function (err, result) {
          if (err) throw err;
          console.log(`New barrage added.`);
        });
      }
    });
  });
}catch (error) {
  console.log('An error occurred:', error.message);
}
}

function addNoeud(adress)
{
const con = mysql.createConnection({
  host: "192.168.1.199",
  user: "root",
  password: "",
  database: "pi4sleam"
});

 con.connect(function(err) {
    if (err) {
      //console.error('Error connecting to database: ' + err.stack);
return;
    }
    console.log('Connected to database');
    var sqlSelect = `SELECT COUNT(*) AS count FROM noeud WHERE mac='${hash(adress)}'`;
    con.query(sqlSelect, function (err, result) {
      if (err) throw err;
      if (result[0].count > 0) {
        console.log(`Noeud already exists.`);
      } else {
        var sqlInsert = `INSERT INTO noeud (mac, barage_id) VALUES ('${hash(adress)}',3)`;
        con.query(sqlInsert, function (err, result) {
          if (err) throw err;
          console.log(`New barrage added.`);
        });
      }
    });
  });


}
/*
function getBarrageIdByMac(macaddress)
{
const con = mysql.createConnection({
  host: "192.168.1.110",
  user: "root",
  password: "",
  database: "pi4sleam"
});

 con.connect(function(err) {
    if (err) {
      console.error('Error connecting to database: ' + err.stack);
    }
    console.log('Connected to database');
var sqlSelect = `SELECT id FROM barage WHERE mac='${macaddress}'`;
  con.query(sqlSelect, function(err, result, fields) {
      if (err) {
        console.error('Error executing query: ' + err.stack);
      }
      var value = result[0].id;
      var id = Number(value);
        return id;
    });
});
}
*/

function getNoeudIdByMac(address) {
  const con = mysql.createConnection({
    host: "192.168.1.199",
    user: "root",
    password: "",
    database: "pi4sleam"
  });

  return new Promise((resolve, reject) => {
    con.connect(function(err) {
      if (err) {
        console.error('Error connecting to database: ' + err.stack);
        reject(err);
      } else {
        console.log('Connected to database');
        var sqlSelect = `SELECT id FROM noeud WHERE mac='${address}'`;
        con.query(sqlSelect, function(err, result, fields) {
          if (err) {
            console.error('Error executing query: ' + err.stack);
            reject(err);
          } else {
            var value = result[0].id;
            var id = Number(value);
            resolve(id);
          }
          con.end();
        });
      }
    });
  });
}


function getBarrageIdByMac(macaddress)
{
  return new Promise((resolve, reject) => {
    const con = mysql.createConnection({
      host: "192.168.1.199",
      user: "root",
      password: "",
      database: "pi4sleam"
    });

    con.connect(function(err) {
      if (err) {
        reject('Error connecting to database: ' + err.stack);
      } else {
        console.log('Connected to database');
        var sqlSelect = `SELECT id FROM barage WHERE mac='${macaddress}'`;
        con.query(sqlSelect, function(err, result, fields) {
          if (err) {
            reject('Error executing query: ' + err.stack);
          } else {
            var value = result[0].noeud_id;
            var id = Number(value);
            resolve(id);
          }
        });
      }
    });
  });

}

function updateColumn() {
const con = mysql.createConnection({
  host: "192.168.1.199",
  user: "root",
  password: "",
  database: "pi4sleam"
});
var filePath = "../data.txt";
 var niveau = readTextFile(filePath);

  const query = 'UPDATE barage SET niveau = ${niveau} WHERE mac = ${macaddress}';
  con.query(query, (err, result) => {
    if (err) {
      console.error(err);
      return;
    } else {
      console.log(`Updated ${result.affectedRows} rows`);
    }
  });
}

/*
async function getBarrageIdByMac(macaddress) {
  try {
    await con.connect();
    var sqlSelect = `SELECT id FROM barage WHERE mac='${macaddress}'`;
    var result = await query(sqlSelect);
    var value = result[0].id;
    var id = Number(value);
    return id;
  } catch (error) {
    console.error('Error:', error);
    throw error;
  }
}*/