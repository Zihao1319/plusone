import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-aioptions',
  templateUrl: './aioptions.component.html',
  styleUrls: ['./aioptions.component.css'],
})
export class AioptionsComponent {
  selectedPrompt: any;

  constructor(
    private router: Router,
    public dialogRef: MatDialogRef<String>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  closeDialog() {
    if (this.selectedPrompt) {
      this.dialogRef.close(this.selectedPrompt);
    } else {
      this.dialogRef.close(null);
    }
  }
}
