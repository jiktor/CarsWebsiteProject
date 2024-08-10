import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAdvertsComponent } from './my-adverts.component';

describe('MyAdvertsComponent', () => {
  let component: MyAdvertsComponent;
  let fixture: ComponentFixture<MyAdvertsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyAdvertsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MyAdvertsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
