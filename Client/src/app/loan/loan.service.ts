import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Loan } from './model/Loan';
import { LoanPage } from './model/LoanPage';
import { HttpClient } from '@angular/common/http';
import { Pageable } from '../core/model/page/Pageable';


@Injectable({
  providedIn: 'root'
})
export class LoanService {
  constructor(        
    private http: HttpClient
  ) {}

  private baseUrl = 'http://localhost:8080/loan';

  
  getFilteredPagedLoans(request: any): Observable<LoanPage> {
    return this.http.post<LoanPage>(this.baseUrl, request);
  }

  saveLoan(loan: Loan): Observable<Loan> {
      const { id } = loan;
      const url = id ? `${this.baseUrl}/${id}` : this.baseUrl;
      return this.http.put<Loan>(url, loan);
  }

  deleteLoan(idLoan: number): Observable<void> {
      return this.http.delete<void>(`${this.baseUrl}/${idLoan}`);
  }

}
