// import { Injectable } from '@angular/core';
// import {
//   HttpInterceptor,
//   HttpRequest,
//   HttpHandler,
//   HttpEvent,
// } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { UserManagementService } from './services/user.management.service';

// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {
//   constructor(private UserSvc: UserManagementService) {}

//   intercept(
//     request: HttpRequest<any>,
//     next: HttpHandler
//   ): Observable<HttpEvent<any>> {
//     // Get the token from wherever you have stored it (e.g., localStorage, sessionStorage, etc.)
//     const token = this.UserSvc.token;
//     console.log(token);

//     if (token) {
//       request = request.clone({
//         setHeaders: {
//           Authorization: `Bearer ${token}`,
//         },
//       });
//       console.log(request);
//     }

//     return next.handle(request);
//   }
// }
