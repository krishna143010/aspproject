import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RootTestComponent } from './root-test.component';

describe('RootTestComponent', () => {
  let component: RootTestComponent;
  let fixture: ComponentFixture<RootTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RootTestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RootTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
