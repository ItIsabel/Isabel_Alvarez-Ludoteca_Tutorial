
import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Loan } from '../model/Loan'
import { LoanService } from '../loan.service'
import { ChangeDetectionStrategy } from '@angular/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, provideNativeDateAdapter } from '@angular/material/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loan-edit',
  standalone: true,
  imports: [
    CommonModule, // Necesario para @if y otras directivas
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule, 
    MatInputModule, 
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [
    provideNativeDateAdapter() // Proveído correctamente
  ],
  templateUrl: './loan-edit.component.html',
  styleUrl: './loan-edit.component.scss',
})

export class LoanEditComponent implements OnInit {
  loan: Loan;

  constructor(
      public dialogRef: MatDialogRef<LoanEditComponent>,
      @Inject(MAT_DIALOG_DATA) public data: {loan : Loan},
      private loanService: LoanService
  ) {}

  ngOnInit(): void {
    this.loan = this.data.loan ? Object.assign({}, this.data.loan) : new Loan();
    
    if (!this.loan.game) {
      this.loan.game.title = '' ;
    }
    
    if (!this.loan.customer) {
      this.loan.customer.name = '';
    }
  }

  onSave() {
      this.loanService.saveLoan(this.loan).subscribe(() => {
          this.dialogRef.close(true); // Pasar true para indicar que se guardó
      });
  }

  onClose() {
      this.dialogRef.close(false); // Pasar false para indicar que se canceló
  }
}
