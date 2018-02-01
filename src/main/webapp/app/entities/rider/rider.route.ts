import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RiderComponent } from './rider.component';
import { RiderDetailComponent } from './rider-detail.component';
import { RiderPopupComponent } from './rider-dialog.component';
import { RiderDeletePopupComponent } from './rider-delete-dialog.component';

export const riderRoute: Routes = [
    {
        path: 'rider',
        component: RiderComponent,
        data: {
            authorities: ['ROLE_USER']
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rider/:id',
        component: RiderDetailComponent,
        data: {
            authorities: ['ROLE_USER']
        },
        canActivate: [UserRouteAccessService]
    }
];

export const riderPopupRoute: Routes = [
    {
        path: 'rider-new',
        component: RiderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Riders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rider/:id/edit',
        component: RiderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Riders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rider/:id/delete',
        component: RiderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Riders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
