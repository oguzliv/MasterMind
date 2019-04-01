import { Component, OnInit } from '@angular/core';
import { LogicService } from '../logic.service';
import { ListItem } from '../listItem';

@Component({
  selector: 'app-computer',
  templateUrl: './computer.component.html',
  styleUrls: ['./computer.component.css']
})

/**
 * This class handles and implements computer logic for the game
 */
export class ComputerComponent implements OnInit {
  hintStr: string;
  computerPrediction: string;
  dataSource: ListItem[] = [];
  dummyVar: number;

  /**
   * Injecting logicService 
   * @param logicService is injected dependency 
   */
  constructor(private logicService: LogicService ) { }

  /**
   * There are 3 data streams used. Hint data stream handles
   * computer table update and computer hint display. Prediction stream
   * is used for updateing computer's prediction. Table stream
   * is used for just updating table.
   */
  ngOnInit() {
    this.logicService.currentComputerHint.subscribe(hint => {
      console.log('computer component hint change ' +
                  'player prediction : ' + this.logicService.getPlayerPrediction());
      if (hint !== null) {
        const pos = hint.getPositive();
        const neg = hint.getNegative();
        this.hintStr = 'POS : ' + pos + ' NEG : ' + neg;
        console.log(this.hintStr);
        this.dataSource = this.logicService.getComputerData();
      } else {
        this.hintStr = 'Computer hint to player..';
        this.dataSource = [];
      }
    });
    this.logicService.currentComputerPrediction.subscribe(prediction => {
      this.computerPrediction = prediction;
    });
    this.logicService.currentTable.subscribe(num => {
      this.dataSource = this.logicService.getComputerData();
    });
   }
}
