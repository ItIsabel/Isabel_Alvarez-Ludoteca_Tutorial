
import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Loan } from '../model/Loan'
import { LoanService } from '../loan.service'
import { MatCardActions } from '@angular/material/card';
import { ChangeDetectionStrategy } from '@angular/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-loan-edit',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule, 
    MatInputModule, 
    MatButtonModule,
    MatDatepickerModule,
    MatCardActions
    ,],
  templateUrl: './loan-edit.component.html',
  styleUrl: './loan-edit.component.scss'
})

export class LoanEditComponent implements OnInit {
  loan: Loan;
  finishDateControl

  constructor(
      public dialogRef: MatDialogRef<LoanEditComponent>,
      @Inject(MAT_DIALOG_DATA) public data: {loan : Loan},
      private loanService: LoanService
  ) {}

  ngOnInit(): void {
    this.loan = this.data.loan ? Object.assign({}, this.data.loan) : new Loan();
  }

  onSave() {
      this.loanService.saveLoan(this.loan).subscribe(() => {
          this.dialogRef.close();
      });
  }

  onClose() {
      this.dialogRef.close();
  }
}
