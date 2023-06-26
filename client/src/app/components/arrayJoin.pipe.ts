import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'arrayJoin',
})
export class ArrayJoinPipe implements PipeTransform {
  transform(value: any[]): string {
    const titleCaseArray = value.map(
      (item) => item.charAt(0).toUpperCase() + item.slice(1).toLowerCase()
    );
    return titleCaseArray.join(', ');
  }
}
