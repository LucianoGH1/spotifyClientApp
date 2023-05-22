import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registrationForm: FormGroup;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
  ) {}

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  get f() {
    return this.registrationForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    console.log(this.registrationForm.value)
    if (this.registrationForm.invalid) {
      return;
    }

    const { email, password } = this.registrationForm.value;

    this.authService.registrar(email, password).subscribe({
      next: (res) => {
        console.log(res);
        this.registrationForm.reset();
        this.submitted = false;
        alert('Usuario registrado!');
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

}
