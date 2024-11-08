const WebSocket = require('ws');
const http = require('http');
const wss = new WebSocket.Server({ port: 1234 });

wss.on('connection', function connection(ws) {
    ws.on('message', function incoming(message) {
        // 모든 연결된 클라이언트에게 메시지 브로드캐스트
        wss.clients.forEach(function each(client) {
            if (client !== ws && client.readyState === WebSocket.OPEN) {
                client.send(message);
            }
        });
    });
});