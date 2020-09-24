import { ValidatorFn, AbstractControl } from '@angular/forms';
/* eslint-disable */

export function dateNotBefore(date: Date): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const chosenDate = control.value as Date;
    // to dd.mm.yyyy
    const chosenFormatted = chosenDate.toJSON().slice(0, 10);
    const givenFormatted = date.toJSON().slice(0, 10);

    console.log(chosenFormatted, givenFormatted, chosenFormatted <= givenFormatted);

    return chosenFormatted <= givenFormatted ? { minDate: { value: control.value } } : null;
  };
}
