import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { LogicService } from '../logic.service';
import { delay } from 'q';

@Component({
  selector: 'app-info-desk',
  templateUrl: './info-desk.component.html',
  styleUrls: ['./info-desk.component.css']
})
/**
 * This class handle and implements logic user
 * user information and error logs.
 */
export class InfoDeskComponent implements OnInit {

  statusMessage: string;
  gameStarted: boolean;
  turn = 0;

  /**
   * Dependency injection as logicService object
   * @param logicService injected dependency
   */
  constructor(private logicService: LogicService) { }

  /**
   * There are 5 streams used for informing user. 
   * currentStatus, currentGameStatus, currentPlayerTurn, currentComputerTurn and currentTurn.
   * gameStatus, player and computer turns are boolean, turn is integer and status is string.
   * Aim of these data streams are proper dynmic data update.
   */
  ngOnInit() {
    this.logicService.currentGameStatus.subscribe(gameStatus => {
      this.logicService.setGameStarted(gameStatus);
      this.gameStarted = gameStatus;
    });
    this.logicService.currentPlayerTurn.subscribe(turn => {
      this.logicService.setUserTurn(turn);
    });
    this.logicService.currentComputerTurn.subscribe(turn => {
      this.logicService.setComputerTurn(turn);
    });
    this.logicService.currentStatus.subscribe(message => {
      this.statusMessage = message;
    });
    this.logicService.currentTurn.subscribe(turn => {
      this.turn = turn;
    });
  }
  /**
   * Event handler for start game button. Starts the game according user or computer.
   * Handles some UI componenets enable and diasble operations.
   */
  startGame(): void {
    this.turn = 0;
    let start: number;
    start = Math.floor(Math.random() * 2) + 1;
    console.log('Start : ' + start);
    this.logicService.changeGameStarted(true);
    this.logicService.setPlayerData([]);
    this.logicService.setComputerData([]);
    this.logicService.changePlayerPrediction('');
    this.logicService.changeComputerHint(null);
    if (start === 1) {
      this.logicService.changeStatus('User starts!');
      this.logicService.changeStatus('User predicts..');
      this.logicService.changeUserTurn(true);
    } else {
      this.logicService.changeStatus('Computer Starts');
      this.logicService.changeStatus('User hints..');
      this.logicService.changeComputerTurn(true);
      this.logicService.computerPredicts();
      this.logicService.changeComputerPrediction(this.logicService.getComputerPrediction());
    }
  }

}
