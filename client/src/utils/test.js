import { render } from '@testing-library/react';
const customRender = (ui, wrapper, options) => render(ui, { wrapper, ...options });

export { customRender as render };
export * from '@testing-library/react';
