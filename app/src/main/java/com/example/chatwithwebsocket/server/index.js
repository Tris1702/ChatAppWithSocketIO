const express = require('express')
const http = require('http')
const socket = require('socket.io')
const app = express()
const server = http.createServer(app)
io = socket(server)


io.on('connection', (socket) =>{
    console.log('user connected')
    socket.on("join", (userName) => {
        console.log(userName + ' : has joined the chat ')
        socket.broadcast.emit('userjoinedthechat', userName + ' : has joined the chat ')
    })
    socket.on('messagedetection', (senderName, messageContent) => {
        let message = {
            'senderName': senderName,
            'message': messageContent
        }
        io.emit('message', message)
    })
    
    socket.on('disconnect', ()=>{
        console.log('user has left')
        socket.broadcast.emit('userdisconnect', ' user has left')
    })
})

server.listen(3000, ()=>{
    console.log('Node app is running on port 3000')
})