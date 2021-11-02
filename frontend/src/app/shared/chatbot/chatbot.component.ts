import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChatBotService } from "../../services/chat-bot.service";

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.scss']
})
export class ChatbotComponent implements OnInit {

  private service: ChatBotService;
  listMsg = [];
  constructor(private router: Router, private serviceCHatBot: ChatBotService) {
    this.service = serviceCHatBot;
  }

  ngOnInit() {

  }
  getPath() {
    return this.router.url;
  }
  sendMesg(event, userInput, containerMsg) {
    event.preventDefault();
    this.listMsg.push({
      "who": "user",
      "message": userInput.value
    })
    containerMsg.scrollTop = containerMsg.scrollHeight;
    this.service.getAnswer(userInput.value)
      .then(rsp => {
        console.log(rsp)
        this.listMsg.push({
          "who": "h-bot",
          "message": rsp["rsp"]
        })
      })
      containerMsg.scrollTop = containerMsg.scrollHeight;
    userInput.value = '';
  }
}
