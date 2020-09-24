import { NativeDateAdapter } from '@angular/material/core';

export class CustomDatePickerAdapter extends NativeDateAdapter {
  getFirstDayOfWeek(): number {
    // We can't tell using native JS Date what the first day of the week is, we default to Sunday.
    return 1;
  }
}
