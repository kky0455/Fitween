import { render, screen } from '../../../utils/test';
import Button from './Button';

test('버튼 라벨 테스트', () => {
	render(<Button label="test" />);
	const btnEl = screen.getByRole('button');
	expect(btnEl).toHaveTextContent('test');
});
