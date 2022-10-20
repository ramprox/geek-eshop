import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class AppConfig {

}

export class FrontUrls {
  static readonly MAIN = 'main';
  static readonly SIGN_UP = 'signup';
  static readonly SIGN_IN = 'signin';
  static readonly CART = 'cart';
  static readonly USERS = 'users';
  static readonly PROFILE = FrontUrls.USERS + '/:id';
}
