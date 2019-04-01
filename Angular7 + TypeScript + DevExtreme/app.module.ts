import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { DxDataGridModule } from 'devextreme-angular';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PlayerComponent } from './player/player.component';
import { InfoDeskComponent } from './info-desk/info-desk.component';
import { ComputerComponent } from './computer/computer.component';

@NgModule({
  declarations: [
    AppComponent,
    PlayerComponent,
    InfoDeskComponent,
    ComputerComponent
  ],
  imports: [
    BrowserModule,
    DxDataGridModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
