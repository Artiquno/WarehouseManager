import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { AuthenticationService } from './authentication.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'Warehouse Manager';

    constructor(private usersService: AuthenticationService) { }

    createDefaultUser(button: HTMLButtonElement): void {
        this.usersService.createDefaultUser()
            .subscribe((resp) => {
                button.textContent = "Done";
                button.disabled = true;
                console.log("User created");
            });
    }
}
