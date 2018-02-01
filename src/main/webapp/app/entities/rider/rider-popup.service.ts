import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Rider } from './rider.model';
import { RiderService } from './rider.service';

@Injectable()
export class RiderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private riderService: RiderService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.riderService.find(id).subscribe((rider) => {
                    if (rider.dateOfBirth) {
                        rider.dateOfBirth = {
                            year: rider.dateOfBirth.getFullYear(),
                            month: rider.dateOfBirth.getMonth() + 1,
                            day: rider.dateOfBirth.getDate()
                        };
                    }
                    this.ngbModalRef = this.riderModalRef(component, rider);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.riderModalRef(component, new Rider());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    riderModalRef(component: Component, rider: Rider): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rider = rider;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
