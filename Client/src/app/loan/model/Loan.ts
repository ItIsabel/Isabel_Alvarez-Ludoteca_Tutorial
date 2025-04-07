import { Game } from '../../game/model/Game';
import { Customer } from '../../customer/model/Customer';

export class Loan {
    id: number;
    game: Game;
    customer: Customer;
    startDate: Date;
    finishDate: Date;
    
}