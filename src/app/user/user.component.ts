import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { UtililtyFunctions } from '../utils/utils';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  message;
  name:any;
  constructor(private userService: UserService,private utils: UtililtyFunctions) { }

  ngOnInit(): void {
    //this.forUser();
    let loginedUserData = this.utils.getUserMeData();
    if (loginedUserData && loginedUserData != null) {
      this.name=loginedUserData.fname;
      }
  }

  forUser() {
    this.userService.forUser().subscribe(
      (response) => {
        console.log(response);
        this.message = response;
      }, 
      (error)=>{
        console.log(error);
      }
    );
  }
}
