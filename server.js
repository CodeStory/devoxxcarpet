var appengine = require('appengine');
var express = require('express');

var app = express();

app.use(appengine.middleware.base);

app.get('/_ah/health', function(req, res) {
  res.set('Content-Type', 'text/plain');
  res.send(200, 'ok');
});

app.get('/_ah/start', function(req, res) {
  res.set('Content-Type', 'text/plain');
  res.send(200, 'ok');
});

app.get('/_ah/stop', function(req, res) {
  res.set('Content-Type', 'text/plain');
  res.send(200, 'ok');
  process.exit();
});

app.get('/carpets/match', function(req, res) {
	var carpets = [];
	carpets.push({'index': 1, 'score': 100, 'votes': 10});
	carpets.push({'index': 2, 'score': 200, 'votes': 5});
  res.send(JSON.stringify(carpets));
});

app.get('/carpets/top', function(req, res) {
	var top = {};
	top['1'] = {'rank': 1, 'carpet': {'index': 1, 'score': 100, 'votes': 10}};
	top['2'] = {'rank': 2, 'carpet': {'index': 2, 'score': 100, 'votes': 10}};
	top['3'] = {'rank': 3, 'carpet': {'index': 3, 'score': 100, 'votes': 10}};
	top['4'] = {'rank': 4, 'carpet': {'index': 4, 'score': 100, 'votes': 10}};
	top['5'] = {'rank': 5, 'carpet': {'index': 5, 'score': 100, 'votes': 10}};
  res.send(JSON.stringify(top));
});

app.post('/votes/:winner/:looser', function(req, res) {
  res.send('');
});

app.configure(function(){
	app.use(express.static(__dirname + '/app'));
});

app.listen(8080, '0.0.0.0');
console.log('Listening on port 8080');
