const express = require('express')
const app = express()

app.get('/download', function(req, res){
  var exec = require('child_process').exec;
  var child = exec('java -cp . HTTPConnections',
  function (error, stdout, stderr){
    console.log('Output -> ' + stdout);
    req.end();
    if(error !== null){
      console.log("Error -> "+error);
      req.end();
    }
});

module.exports = child;
});

app.listen(3000)
