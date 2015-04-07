// Generated by CoffeeScript 1.8.0
(function() {
  var CARPET_COUNT, app, appengine, coffee, express, fs, i, k, less, playedPerIndex, scorePerIndex, votesPerIndex;

  appengine = require('appengine');

  express = require('express');

  fs = require('fs');

  coffee = require('coffee-script');

  less = require('less');

  CARPET_COUNT = 12;

  playedPerIndex = (function() {
    var _i, _results;
    _results = [];
    for (i = _i = 0; 0 <= CARPET_COUNT ? _i < CARPET_COUNT : _i > CARPET_COUNT; i = 0 <= CARPET_COUNT ? ++_i : --_i) {
      _results.push(0);
    }
    return _results;
  })();

  scorePerIndex = (function() {
    var _i, _results;
    _results = [];
    for (i = _i = 0; 0 <= CARPET_COUNT ? _i < CARPET_COUNT : _i > CARPET_COUNT; i = 0 <= CARPET_COUNT ? ++_i : --_i) {
      _results.push(1000);
    }
    return _results;
  })();

  votesPerIndex = (function() {
    var _i, _results;
    _results = [];
    for (i = _i = 0; 0 <= CARPET_COUNT ? _i < CARPET_COUNT : _i > CARPET_COUNT; i = 0 <= CARPET_COUNT ? ++_i : --_i) {
      _results.push(0);
    }
    return _results;
  })();

  app = express();

  app.use(express["static"](__dirname + '/app'));

  app.use(appengine.middleware.base);

  app.get('/_ah/health', function(req, res) {
    res.set('Content-Type', 'text/plain');
    return res.send(200, 'ok');
  });

  app.get('/_ah/start', function(req, res) {
    res.set('Content-Type', 'text/plain');
    return res.send(200, 'ok');
  });

  app.get('/_ah/stop', function(req, res) {
    res.set('Content-Type', 'text/plain');
    res.send(200, 'ok');
    return process.exit();
  });

  app.get('/carpets/match', function(req, res) {
    var carpets, left, right, _ref;
    while (left === right) {
      _ref = [Math.floor(Math.random() * CARPET_COUNT), Math.floor(Math.random() * CARPET_COUNT)], left = _ref[0], right = _ref[1];
    }
    carpets = [
      {
        index: left,
        score: scorePerIndex[left],
        votes: votesPerIndex[left]
      }, {
        index: right,
        score: scorePerIndex[right],
        votes: votesPerIndex[right]
      }
    ];
    return res.send(JSON.stringify(carpets));
  });

  app.get('/carpets/top', function(req, res) {
    var carpet, carpets, rank, top, _i;
    carpets = (function() {
      var _i, _results;
      _results = [];
      for (i = _i = 0; 0 <= CARPET_COUNT ? _i < CARPET_COUNT : _i > CARPET_COUNT; i = 0 <= CARPET_COUNT ? ++_i : --_i) {
        _results.push({
          index: i,
          votes: votesPerIndex[i]
        });
      }
      return _results;
    })();
    carpets.sort(function(l, r) {
      return r.votes - l.votes;
    });
    top = {};
    for (rank = _i = 0; 0 <= CARPET_COUNT ? _i < CARPET_COUNT : _i > CARPET_COUNT; rank = 0 <= CARPET_COUNT ? ++_i : --_i) {
      carpet = carpets[rank];
      top[carpet.index] = {
        rank: rank,
        carpet: carpet
      };
    }
    return res.send(JSON.stringify(top));
  });

  app.get('/js/controllers.js', function(req, res) {
    var cs, js;
    cs = fs.readFileSync("" + __dirname + "/app/js/controllers.coffee", 'UTF-8');
    js = coffee.compile(cs);
    res.header('Content-Type', 'application/x-javascript');
    return res.send(js);
  });

  app.get('/css/style.css', function(req, res) {
    var source;
    source = fs.readFileSync("" + __dirname + "/app/css/style.less", 'UTF-8');
    return less.render(source, function(e, output) {
      res.header('Content-Type', 'text/css');
      return res.send(output.css);
    });
  });

  k = function(score, played) {
    if (played < 30) {
      return 25;
    } else {
      if (score < 2400) {
        return 15;
      } else {
        return 10;
      }
    }
  };

  app.post('/votes/:winner/:looser', function(req, res) {
    var d, k1, k2, looser, p, r1, r2, score1, score2, winner;
    winner = +req.params.winner;
    looser = +req.params.looser;
    score1 = scorePerIndex[winner];
    score2 = scorePerIndex[looser];
    d = Math.min(400, Math.abs(score1 - score2));
    p = 1.0 / (1.0 + Math.pow(10, -d / 400.0));
    k1 = k(score1, ++playedPerIndex[winner]);
    k2 = k(score2, ++playedPerIndex[looser]);
    r1 = Math.round(score1 + (k1 * (1 - p)));
    r2 = Math.round(score2 + (k2 * (0 - p)));
    votesPerIndex[winner]++;
    scorePerIndex[winner] = r1;
    scorePerIndex[looser] = r2;
    return res.send('');
  });

  app.listen(8080);

}).call(this);