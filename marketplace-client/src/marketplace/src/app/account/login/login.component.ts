import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import { first } from 'rxjs/operators';

import { AccountService } from '../../_services/account.service';

@Component(
  { selector: 'app-login',
    templateUrl: 'login.component.html' ,
    styleUrls: ['login.component.css']}
  )
export class LoginComponent{}
