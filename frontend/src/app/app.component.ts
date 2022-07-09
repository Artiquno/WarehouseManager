import { Component } from '@angular/core';
import { AuthenticationService } from './authentication.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'Warehouse Manager';

    constructor(private usersService: AuthenticationService) { }

    createDefaultUser(): void {
        this.usersService.createDefaultUser()
            .subscribe(() => console.log("User created"));
    }
}
