import {ChangeDetectionStrategy, Component, signal} from '@angular/core';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {merge} from 'rxjs';


@Component({
  selector: 'app-login',
  templateUrl: './login-component.html',
  styleUrl: './login-component.scss',
  imports: [MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule,MatCardModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent{
  readonly password = new FormControl('', [Validators.required, Validators.requiredTrue]);
  readonly usr = new FormControl('', [Validators.required, Validators.requiredTrue]);

  errorMessage = signal('');

  constructor() {
    merge(this.password.statusChanges, this.password.valueChanges)
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateErrorMessage());
  }

  signIn(){
    console.log(this.password.value);
  }

  updateErrorMessage() {
    if (this.password.hasError('required')) {
      this.errorMessage.set('You must enter a value');
    } else if (this.password.hasError('email')) {
      this.errorMessage.set('Not a valid email');
    } else {
      this.errorMessage.set('');
    }
  }
}