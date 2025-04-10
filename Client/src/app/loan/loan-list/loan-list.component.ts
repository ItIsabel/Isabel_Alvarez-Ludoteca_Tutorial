import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepicker, MatDatepickerToggle } from '@angular/material/datepicker';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { Pageable } from '../../core/model/page/Pageable';
import { DialogConfirmationComponent } from '../../core/dialog-confirmation/dialog-confirmation.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';

import { LoanEditComponent } from '../loan-edit/loan-edit.component';
import { LoanService } from '../loan.service';
import { CustomerService } from '../../customer/customer.service';
import { GameService } from '../../game/game.service';

import { Loan } from '../model/Loan';
import { Game } from '../../game/model/Game'
import { Customer } from '../../customer/model/Customer';


@Component({
  selector: 'app-loan-list',

  imports: [
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDatepickerToggle,
    MatPaginatorModule
  ],
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.scss']
})
export class LoanListComponent {
  customers: Customer[];
  loans: Loan[];
  games: Game[];

  //datos para el filtrado
  filterCustomer: Customer;
  filterGame: Game;
  filterDate: Date;

  //datos para el paginado
  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0 ;

  dataSource = new MatTableDataSource<Loan>();
  displayedColumns: string[] = ['id', 'titleGame', 'nameCustomer', 'startDate' , 'finishDate' , 'action'];


  constructor(
      private loanService: LoanService,
      private customerService: CustomerService,
      private gameService: GameService,
      public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadFilteredPage();
    this.gameService.getGames().subscribe((gamesR) => (this.games = gamesR));

    //obtengo un array de customers (customersR) y se lo asigno a mi variable customers
    this.customerService.getCustomers().subscribe((customersR) => (this.customers = customersR));

}

loadFilteredPage(event?: PageEvent) {
    const pageable: Pageable = {
        pageNumber: this.pageNumber,
        pageSize: this.pageSize,
        sort: [
            {
                property: 'id',
                direction: 'ASC',
            },
        ],
    };

    if (event != null) {
        pageable.pageSize = event.pageSize;
        pageable.pageNumber = event.pageIndex;
    }

      // Creamos un objeto para enviar los filtros junto con la paginación
  const request = {
    pageable: pageable,
    titleGame: this.filterGame?.title || null,
    nameCustomer: this.filterCustomer?.name || null,
    requestDate: this.filterDate || null
    
  };

    this.loanService.getFilteredPagedLoans(request).subscribe((data) => {
        this.dataSource.data = data.content;
        this.pageNumber = data.pageable.pageNumber;
        this.pageSize = data.pageable.pageSize;
        this.totalElements = data.totalElements;
    });
}


  onCleanFilter(): void {
    this.filterGame = null;
    this.filterCustomer = null;
    this.filterDate = null;
    this.onSearch();
  }

  onSearch(): void {
      this.loadFilteredPage();
  }

  createLoan() {
      const dialogRef = this.dialog.open(LoanEditComponent, {
          data: {},
      });

      dialogRef.afterClosed().subscribe((result) => {
          this.ngOnInit();
      });
  }
  
  editLoan(loanparam: Loan) {
      const dialogRef = this.dialog.open(LoanEditComponent, {
          data: { loan: loanparam },
      });

      dialogRef.afterClosed().subscribe((result) => {
          this.onSearch();
      });
  }

deleteLoan(loan: Loan) {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
        data: {  title: 'Eliminar préstamo', description: 'Atención: si borra el préstamo se perderán sus datos.<br> ¿Desea eliminar el préstamo?',
        },
    });

    dialogRef.afterClosed().subscribe((result) => {
        if (result) {
            this.loanService.deleteLoan(loan.id).subscribe((result) => {
                this.ngOnInit();
            });
        }
    });

}
}
