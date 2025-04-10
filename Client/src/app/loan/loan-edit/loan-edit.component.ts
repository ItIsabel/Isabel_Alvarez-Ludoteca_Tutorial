
import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldControl, MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ChangeDetectionStrategy } from '@angular/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, provideNativeDateAdapter } from '@angular/material/core';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';

import { LoanService } from '../loan.service';
import { CustomerService } from '../../customer/customer.service';
import { GameService } from '../../game/game.service';

import { Game } from '../../game/model/Game';
import { Loan } from '../model/Loan';
import { Customer } from '../../customer/model/Customer';



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
    MatNativeDateModule,
    MatSelectModule
  ],
  providers: [
    provideNativeDateAdapter() // Proveído correctamente
  ],
  templateUrl: './loan-edit.component.html',

})

export class LoanEditComponent implements OnInit {
  loan: Loan; 
  customers: Customer[];
  games: Game[];

  //datos para el filtrado
  nameCustomer: Customer;
  titleGame: Game;
  startDate: Date;
  finishDate: Date;
  dateRangeInvalid = false;
  

  constructor(
      public dialogRef: MatDialogRef<LoanEditComponent>,
      @Inject(MAT_DIALOG_DATA) public data: {loan : Loan},
      private loanService: LoanService,
      private customerService: CustomerService,
      private gameService: GameService,
  ) {}

  ngOnInit(): void {
    // Corregimos la estructura del condicional
    if (this.data && this.data.loan) {
      this.loan = Object.assign({}, this.data.loan);
    } else {
      this.loan = new Loan();
    }
    
    // Cargamos los juegos y clientes
    this.gameService.getGames().subscribe((gamesR) => {
      this.games = gamesR;
      // Intentamos buscar el juego después de cargar la lista
      this.trySetGameAndCustomer();
    });
    
    this.customerService.getCustomers().subscribe((customersR) => {
      this.customers = customersR;
      // Intentamos buscar el cliente después de cargar la lista
      this.trySetGameAndCustomer();
    });
  }
  
  // Método auxiliar para intentar establecer el juego y cliente
  private trySetGameAndCustomer(): void {
    // Verificamos que tengamos los datos necesarios cargados
    if (!this.games || !this.customers || !this.loan.game) {
      return;
    }
    
    // Verificamos que el juego tenga un título antes de intentar acceder
    if (this.loan.game && this.loan.game.title && this.loan.game.title.length > 0) {
      // Buscamos el juego en la lista
      this.titleGame = this.games.find(game => game.id === this.loan.game.id);
      
      // Buscamos el cliente en la lista
      if (this.loan.customer) {
        this.nameCustomer = this.customers.find(customer => customer.id === this.loan.customer.id);
      }
      
      // Convertimos las fechas a objetos Date
      if (this.loan.startDate) {
        this.startDate = new Date(this.loan.startDate);
      }
      
      if (this.loan.finishDate) {
        this.finishDate = new Date(this.loan.finishDate);
      }
    }
  }

  validateDateRange(): void {
    if (this.loan.startDate && this.loan.finishDate) {
      const startDate = new Date(this.loan.startDate);
      const finishDate = new Date(this.loan.finishDate);
      
      // Calcular la diferencia en milisegundos
      const differenceMs = finishDate.getTime() - startDate.getTime();
      
      // Convertir a días
      const differenceDays = differenceMs / (1000 * 60 * 60 * 24);
      
      if (differenceMs < 0) {
        // La fecha de fin es anterior a la fecha de inicio
        this.dateRangeInvalid = true;
        console.error('La fecha de inicio no puede ser posterior a la fecha de fin');
      } else if (differenceDays >= 14) {
        // El préstamo excede los 14 días
        this.dateRangeInvalid = true;
        console.error('El período de préstamo no puede exceder los 14 días');
      } else {
        // Fecha válida
        this.dateRangeInvalid = false;
      }
    } else {
      // Fechas no establecidas
      this.dateRangeInvalid = true;
    }
  }
  onSave(): void {
    if (!this.dateRangeInvalid) {
      try { 
        this.loan.customer=this.nameCustomer
        this.loan.game=this.titleGame
        /** 
        startDate: this.startDate || null
        finishDate: this.finishDate || null
        */
        this.loan.startDate=this.startDate.toISOString().split('T')[0];
        this.loan.finishDate=this.finishDate.toISOString().split('T')[0];

        this.loanService.saveLoan(this.loan).subscribe((result) => {
          this.dialogRef.close();
      })
      }      

       catch (error){
        console.error('Error al guardar el préstamo:', error);
       }
    }
    }

  onClose() {
      this.dialogRef.close(); // Pasar false para indicar que se canceló
  }
}
