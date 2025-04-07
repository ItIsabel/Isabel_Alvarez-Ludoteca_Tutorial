import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Customer } from '../model/Customer';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CustomerService } from '../customer.service';
import { MatDialog } from '@angular/material/dialog';
import { CustomerEditComponent } from '../customer-edit/customer-edit.component';
import { DialogConfirmationComponent } from '../../core/dialog-confirmation/dialog-confirmation.component';


@Component({
  selector: 'app-customer-list',
  imports: [
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    CommonModule
  ],
  templateUrl: './customer-list.component.html',

})
export class CustomerListComponent {
  dataSource = new MatTableDataSource<Customer>();
  displayedColumns: string[] = ['id', 'name', 'action'];

  constructor(
    private customerService: CustomerService,
    public dialog: MatDialog,
  ) { }


  ngOnInit(): void {
      this.customerService.getCustomers().subscribe(
          customers => this.dataSource.data = customers
      );
  }

  createCustomer() {    
    const dialogRef = this.dialog.open(CustomerEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });    
  }  

  editCustomer(customer: Customer) {
    const dialogRef = this.dialog.open(CustomerEditComponent, {
      data: { customer }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }
  deleteCustomer(customer: Customer) {    
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: { title: "Eliminar cliente", description: "Atención si borra el cliente se perderán sus datos.<br> ¿Desea eliminar este cliente?" }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.customerService.deleteCustomer(customer.id).subscribe(result => {
          this.ngOnInit();
        }); 
      }
    });
  }  
}