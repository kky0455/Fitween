import { render, screen } from '../../utils/test';
import DummyPage from './DummyPage';

test('더미페이지 테스트', () => {
	render(<DummyPage />);

	const dummyText = screen.getByText('dummy', { exact: false });
	expect(dummyText).toBeInTheDocument();
});
