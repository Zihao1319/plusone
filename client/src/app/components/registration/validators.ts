import { AbstractControl, ValidationErrors } from '@angular/forms';

export function validateBirthYear(
  control: AbstractControl
): ValidationErrors | null {
  const currentYear = new Date().getFullYear();
  const birthYear = control.value;
  console.log(birthYear);

  if (birthYear >= currentYear || birthYear === 0) {
    return { invalidBirthYear: 'Invalid birth year' };
  }

  if (birthYear.toString().length < 4) {
    return { numInvalid: 'Please key in YYYY format' };
  }

  return null;
}
