import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CustomerService } from '../customer.service';
import { Customer } from '../model/Customer';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-customer-edit',
  imports: [FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule ],
  templateUrl: './customer-edit.component.html',

})
export class CustomerEditComponent {
  customer: Customer;

  constructor(
      public dialogRef: MatDialogRef<CustomerEditComponent>,
      @Inject(MAT_DIALOG_DATA) public data: {customer : Customer},
      private categoryService: CustomerService
  ) {}

  ngOnInit(): void {
    this.customer = this.data.customer ? Object.assign({}, this.data.customer) : new Customer();
  }

  onSave() {
      this.categoryService.saveCustomer(this.customer).subscribe(() => {
          this.dialogRef.close();
      });
  }

  onClose() {
      this.dialogRef.close();
  }
}
