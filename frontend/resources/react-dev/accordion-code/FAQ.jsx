import React, { useState, useEffect } from 'react';
import faqData from './faqData.json'; // Assume we've saved the FAQ data in this file

function FAQ() {
  const [activePanels, setActivePanels] = useState(() => {
    const saved = localStorage.getItem('faqState');
    return saved ? new Set(JSON.parse(saved)) : new Set();
  });

  useEffect(() => {
    localStorage.setItem('faqState', JSON.stringify(Array.from(activePanels)));
  }, [activePanels]);

  const togglePanel = (index) => {
    setActivePanels(prev => {
      const newSet = new Set(prev);
      if (newSet.has(index)) {
        newSet.delete(index);
      } else {
        newSet.add(index);
      }
      return newSet;
    });
  };

  return (
    <div className="faq-container">
      <h2>Frequently Asked Questions</h2>
      {faqData.map((faq, index) => (
        <FAQItem
          key={faq.index}
          question={faq.question}
          isActive={activePanels.has(index)}
          onToggle={() => togglePanel(index)}
        >
          {faq.answer}
        </FAQItem>
      ))}
    </div>
  );
}

function FAQItem({ question, children, isActive, onToggle }) {
  return (
    <div className="faq-item">
      <h3 className="faq-question" onClick={onToggle}>
        {question}
        <span className={`arrow ${isActive ? 'up' : 'down'}`}>
          {isActive ? '▲' : '▼'}
        </span>
      </h3>
      {isActive && <div className="faq-answer">{children}</div>}
    </div>
  );
}

export default FAQ;

