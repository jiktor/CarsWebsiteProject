import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowAdvertsComponent } from './show-adverts.component';

describe('ShowAdvertsComponent', () => {
  let component: ShowAdvertsComponent;
  let fixture: ComponentFixture<ShowAdvertsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShowAdvertsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ShowAdvertsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
