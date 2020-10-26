import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthConfig } from 'angular-oauth2-oidc';
import { OAuthService } from 'angular-oauth2-oidc';
import { NullValidationHandler } from 'angular-oauth2-oidc';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'anguale-oauth2';

  helper = new JwtHelperService();

  user
  data
  isloggedIn:boolean = false

  constructor(private oauthService: OAuthService,private http:HttpClient) {
    this.configure();
  }

  authConfig: AuthConfig = {
    issuer: 'http://localhost:8080/auth/realms/tenant_2',
    redirectUri: window.location.origin + "/",
    clientId: 'angular',
    scope: 'openid profile email offline_access',
    responseType: 'code',
    // at_hash is not present in JWT token
    disableAtHashCheck: true,
    showDebugInformation: true
  }

  public login() {
    this.oauthService.initLoginFlow();
  }

  public logoff() {
    this.oauthService.logOut();
  }

  public userData(){
   this.user =  this.helper.decodeToken(this.oauthService.getAccessToken());
  }

  public getApi(){
  // this.http.get("http://localhost:8089/api/gateway").subscribe(data=>{
  //   this.data = data
  // })
  this.http.get("http://localhost:8089/service1/index1").subscribe(data=>{
    this.data = data
  })
  }

  private configure() {
    this.oauthService.configure(this.authConfig);
    this.oauthService.tokenValidationHandler = new NullValidationHandler();
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
  }

  get accessToken(){
    return this.oauthService.getAccessToken()
  }

  get isLoggedIn(){
    return this.isloggedIn = this.oauthService.hasValidAccessToken()
  }


}
