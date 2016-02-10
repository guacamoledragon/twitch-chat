var socket = io('https://tmi-relay.nightdev.com/');

socket.on('message', function(data) {
  if(getParameterByName('bot_activity').toLowerCase() !== 'true') {
    if(data.message.charAt(0) === '!') return;
    if(/bot$/.test(data.nick)) return;
  }

  Chat.insert(data.nick, data.userData, data.message, data.action, data.emotes);
});

var msg = {
  "action": false,
  "emotes": "68856:26-32",
  "message": "Need more weight in front MingLee",
  "nick": "bowmanlol",
  "userData": {
    "color": "#B22222",
    "displayName": "Bowmanlol",
    "name": "bowmanlol",
    "subscriber": true,
    "turbo": false,
    "userType": "normal"
  }
}

/*socket.on('clearchat', function(user) {
  Chat.clearChat(user);
});*/
