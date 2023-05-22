import { Component, TemplateRef } from '@angular/core';
import { BsModalService, BsModalRef, ModalOptions } from 'ngx-bootstrap/modal';
import { RegisterComponent } from '../register/register.component';
import { LoginComponent } from '../login/login.component';
import { AuthService } from '../auth/auth.service';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  bsModalRef?: BsModalRef;
  isLoggedIn$: Observable<boolean>; 

  constructor(private modalService: BsModalService, private authService: AuthService) {}

  ngOnInit(): void {
    this.isLoggedIn$ = this.authService.isLoggedIn$; 
  }
  openModalWithComponent(accion: string) {
    const modalConfig: ModalOptions = {
      initialState: {},
      class: 'modal-sm', 
      animated: true,
      ignoreBackdropClick: false
    };
    if(accion == 'registrar') {
      this.bsModalRef = this.modalService.show(RegisterComponent, modalConfig);
      this.bsModalRef.content.closeBtnName = 'Close';
    } else if (accion == 'login') {
      this.bsModalRef = this.modalService.show(LoginComponent, modalConfig);
      this.bsModalRef.content.closeBtnName = 'Close';
    }
    
  }

  logout() {
    this.authService.logout();
  }
}
