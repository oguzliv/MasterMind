import { Component, OnInit, OnChanges } from '@angular/core';
import { LogicService } from '../logic.service';
import { Hint } from '../hint';
import { ListItem } from '../listItem';

@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})

/** This class handles player operations and achieves main
 * responsiveness and interactivity for the game.
 */
export class PlayerComponent implements OnInit {
  gameStarted: boolean;
  userTurn: boolean;
  positive: number;
  negative: number;

  dataSource: ListItem[];
  playerData: ListItem[] = [];
  computerData: ListItem[] = [];

  /**
   * Injecting logicService 
   * @param logicService is injected dependency 
   */
  constructor(private logicService: LogicService) {}

  /**
   * There are 3 data streams used: 
   *  userTurn, gameStatus and playerPrediction in order
   *  update these variables' data dynamically. Providing
   * data streams for behaviour subjects in order to provide
   * dynmaic data update. Whenever a data changes occured for
   * these above streams, below codes will be executed.
   */
  ngOnInit() {
    this.logicService.currentPlayerTurn.subscribe(turn => {
      this.userTurn = turn;
      console.log('userTurn : ' + this.userTurn);
    });
    this.logicService.currentGameStatus.subscribe(turn => {
      this.gameStarted = turn;
      console.log('gameStarted : ' + this.gameStarted);
    });
    this.logicService.currentPlayerPrediction.subscribe(prediction => {
      console.log('player prediction changed');
      this.dataSource = this.logicService.getPlayerData();
    });
  }
  /**
   * This function handles user prediction mechnanism. If got any
   * error, reports it. Otherwise, updates user prediction, user's
   * prediction/hint table and computer's hint.
   * @param prediction data came from view of this component.
   */
  getPlayerPrediction(prediction: string): void {
    let myPrediction: number;
    const computerSelection = parseInt(this.logicService.getComputerSelection(), 0);

    myPrediction = parseInt(prediction, 0);
    console.log('Human Prediction : ' + myPrediction);

    if (isNaN (myPrediction)) {
      this.logicService.changeStatus('Non-numeric prediction, make a new one !');
    } else if (myPrediction.toString().length < 4) {
      this.logicService.changeStatus('Prediction has less than 4 digits, make a new one!');
    } else if (myPrediction.toString().length > 4) {
      this.logicService.changeStatus('Prediction has more than 4 digits, make a new one!');
    } else if (!this.logicService.hasSameDigits(myPrediction)) {
      this.logicService.changeStatus('Prediction has identical digits, make a new one!');
    } else if (this.logicService.getAllNumbers().length === 0) {
      this.logicService.changeStatus('Wrong hint at some point, please refresh the page to start over');
    } else if (this.logicService.getPlayerPrediction() === this.logicService.getComputerSelection()) {
      this.logicService.changeStatus('!!WINNER IS PLAYER!!');
      this.logicService.changeGameStarted(false);
    } else {
      const hint = this.logicService.answerHint(myPrediction, computerSelection);
      this.logicService.changeComputerHint(hint);
      const str = 'POS: '  + hint.getPositive() + ' NEG: ' + hint.getNegative();
      const item = new ListItem(myPrediction, str);
      this.playerData.push(item);
      this.logicService.setPlayerData(this.playerData);
      this.logicService.changePlayerPrediction(prediction);

      this.logicService.changeStatus('Computer predicted. User Hints..');
      let turn;
      turn = this.logicService.getTurn();
      this.logicService.setPlayerPrediction(prediction);
      this.userTurn = false;
      turn++;
      this.logicService.setTurn(turn);
      console.log('Turn : ' + turn);
      this.logicService.changeTurn(turn);
      this.logicService.computerPredicts();
      this.logicService.changeComputerPrediction(this.logicService.getComputerPrediction());
      this.logicService.setComputerHintToHuman(hint);
    }
  }
  /**
   * According to pos and neg parameters user hints to computer.
   * According to values of the pos and neg, program either
   * reports error in the UI, or handles appropriate operations if
   * valid pos and neg values are entered.
   * @param pos data that come from the view component
   * @param neg data that come from the view component
   */
  userHintToComputer(pos: string, neg: string): void {
    this.positive = parseInt(pos, 0);
    this.negative = parseInt(neg, 0);
    const sum = this.positive + this.negative;
    const hint = new Hint(this.positive, this.negative);
    if (isNaN(this.positive) || isNaN(this.negative)) {
      this.logicService.changeStatus('Non-numeric hint, please ');
    } else if ((this.positive > 4 || this.positive < 0) && (this.negative > 4 || this.negative < 0) || sum > 4) {
      this.logicService.changeStatus('Invalid hint, POS & NEG are between 0 and 4, both inclusive');
    } else if (this.positive === 4 && this.negative === 0) {
      this.logicService.changeStatus('!!WINNER IS COMPUTER!!');
      this.logicService.changeComputerHint(hint);
      const computerPrediction = parseInt(this.logicService.getComputerPrediction(), 0);
      const str = 'POS : ' + this.positive + ' NEG : ' + this.negative;
      const item = new ListItem(computerPrediction, str);
      this.computerData.push(item);
      this.logicService.setComputerData(this.computerData);
      this.logicService.changeGameStarted(false);
    } else {
      this.logicService.changeStatus('Computer Hinted. User Predicts...');
      const computerPrediction = parseInt(this.logicService.getComputerPrediction(), 0);
      const str = 'POS : ' + this.positive + ' NEG : ' + this.negative;
      const item = new ListItem(computerPrediction, str);
      this.computerData.push(item);
      this.logicService.setComputerData(this.computerData);
      this.logicService.computerPrunes(hint);
      this.logicService.changeUserTurn(true);
      this.logicService.changeComputerTurn(false);
      this.logicService.changeTable(1);
    }
  }
}
