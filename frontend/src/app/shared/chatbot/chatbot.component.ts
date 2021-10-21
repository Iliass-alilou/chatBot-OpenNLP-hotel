import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-chatbot',
    templateUrl: './chatbot.component.html',
    styleUrls: ['./chatbot.component.scss']
})
export class ChatbotComponent implements OnInit {
    
    constructor(private router: Router ) {}

    ngOnInit() {

    }
    getPath(){
      return this.router.url;
    }
    sendMesg(){
      //alert(document.getElementById("idMesg").value)
    }
}
