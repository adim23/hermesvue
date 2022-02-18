/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CodesDetailComponent from '@/entities/codes/codes-details.vue';
import CodesClass from '@/entities/codes/codes-details.component';
import CodesService from '@/entities/codes/codes.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Codes Management Detail Component', () => {
    let wrapper: Wrapper<CodesClass>;
    let comp: CodesClass;
    let codesServiceStub: SinonStubbedInstance<CodesService>;

    beforeEach(() => {
      codesServiceStub = sinon.createStubInstance<CodesService>(CodesService);

      wrapper = shallowMount<CodesClass>(CodesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { codesService: () => codesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCodes = { id: 123 };
        codesServiceStub.find.resolves(foundCodes);

        // WHEN
        comp.retrieveCodes(123);
        await comp.$nextTick();

        // THEN
        expect(comp.codes).toBe(foundCodes);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCodes = { id: 123 };
        codesServiceStub.find.resolves(foundCodes);

        // WHEN
        comp.beforeRouteEnter({ params: { codesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.codes).toBe(foundCodes);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
