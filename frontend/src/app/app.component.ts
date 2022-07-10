import { Component } from '@angular/core';
import { UsersService } from './services/users/users.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'Warehouse Manager';

    constructor(private usersService: UsersService) { }

    createDefaultUser(button: HTMLButtonElement): void {
        this.usersService.createDefaultUser()
            .subscribe((resp) => {
                button.textContent = "Done";
                button.disabled = true;
                console.log("User created");
            });
    }
}
