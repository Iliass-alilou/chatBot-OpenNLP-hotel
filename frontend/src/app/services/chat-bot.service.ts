import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ChatBotService {

  constructor(private http:HttpClient) { }
  
  getAnswer(msg:String){
    const url = environment["API"]+"/message";
    return this.http.post(url,msg).toPromise()
        .then((rsp)=>rsp)
        .catch((err)=>console.log(err));
  }
}
