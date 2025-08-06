'use client';

import { useState, useEffect, useCallback } from 'react';
import {
  getCommentsByPost,
  createComment,
  updateComment,
  deleteComment,
  CommentResponse,
  Page,
} from '@/lib/apis/comment.api'; // API 함수 및 타입 경로

// --- 페이지네이션 UI 컴포넌트 ---
interface PaginationProps {
  currentPage: number; // 현재 페이지 (0부터 시작)
  totalPages: number;  // 전체 페이지 수
  onPageChange: (page: number) => void;
}

const Pagination = ({ currentPage, totalPages, onPageChange }: PaginationProps) => {
  if (totalPages <= 1) return null; // 페이지가 1개 이하면 페이지네이션을 보여주지 않음

  const pageNumbers = Array.from({ length: totalPages }, (_, i) => i);

  return (
    <div style={{ display: 'flex', justifyContent: 'center', gap: '8px', padding: '24px 0' }}>
      {pageNumbers.map((page) => (
        <button
          key={page}
          onClick={() => onPageChange(page)}
          style={{
            padding: '8px 12px',
            border: '1px solid #ddd',
            borderRadius: '4px',
            cursor: 'pointer',
            backgroundColor: currentPage === page ? '#ff6f0f' : 'white',
            color: currentPage === page ? 'white' : 'black',
            fontWeight: currentPage === page ? 'bold' : 'normal',
          }}
        >
          {page + 1}
        </button>
      ))}
    </div>
  );
};


// --- 댓글 하나를 표시하는 아이템 컴포넌트 ---
interface CommentItemProps {
  comment: CommentResponse;
  isEditing: boolean;
  onStartEdit: () => void;
  onCancelEdit: () => void;
  onUpdate: (id: number, content: string) => void;
  onDelete: (id: number) => void;
}

const CommentItem = ({
  comment,
  isEditing,
  onStartEdit,
  onCancelEdit,
  onUpdate,
  onDelete
}: CommentItemProps) => {
  const [editedContent, setEditedContent] = useState(comment.content);

  useEffect(() => {
    if (isEditing) {
      setEditedContent(comment.content);
    }
  }, [isEditing, comment.content]);

  const handleUpdate = () => {
    if (!editedContent.trim()) {
      alert('댓글 내용을 입력해주세요.');
      return;
    }
    onUpdate(comment.id, editedContent);
  };

  const isAuthor = true; // 임시로 모든 댓글에 수정/삭제가 가능하도록 설정

  // [수정] 날짜 포맷팅 함수 (더욱 안정적으로 변경)
  const formatDate = (dateInput: any) => {
    if (Array.isArray(dateInput) && dateInput.length >= 6) {
      // 2. 배열의 각 요소를 사용하여 Date 객체를 생성합니다.
      // 자바스크립트의 월(month)은 0부터 시작하므로, 서버에서 받은 월(1~12)에서 1을 빼줍니다.
      const [year, month, day, hours, minutes, seconds] = dateInput;
      const date = new Date(year, month - 1, day, hours, minutes, seconds);

      // 3. 유효한 날짜인지 확인합니다.
      if (isNaN(date.getTime())) {
        return '잘못된 날짜 형식';
      }

      // 4. 'YYYY.MM.DD HH:mm' 형식으로 변환합니다.
      const formattedMonth = String(date.getMonth() + 1).padStart(2, '0');
      const formattedDay = String(date.getDate()).padStart(2, '0');
      const formattedHours = String(date.getHours()).padStart(2, '0');
      const formattedMinutes = String(date.getMinutes()).padStart(2, '0');

      return `${date.getFullYear()}.${formattedMonth}.${formattedDay} ${formattedHours}:${formattedMinutes}`;
    }

    // 배열이 아닌 다른 형식의 데이터가 들어올 경우를 대비한 방어 코드
    if (typeof dateInput === 'string') {
      const date = new Date(dateInput.replace(" ", "T"));
      if (!isNaN(date.getTime())) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${year}.${month}.${day} ${hours}:${minutes}`;
      }
    }

    // 모든 경우에 해당하지 않으면 기본값을 반환합니다.
    return '날짜 정보 없음';
  };

  return (
    <div style={{ display: 'flex', padding: '12px 0', borderBottom: '1px solid #f2f3f5' }}>
      <div style={{ flex: 1 }}>
        <p style={{ margin: 0, fontWeight: 'bold', fontSize: '0.9rem' }}>{comment.username}</p>

        {isEditing ? (
          // --- 수정 모드 ---
          <div>
            <textarea
              value={editedContent}
              onChange={(e) => setEditedContent(e.target.value)}
              style={{ width: '100%', padding: '8px', border: '1px solid #ccc', borderRadius: '4px', resize: 'vertical', minHeight: '60px', marginTop: '4px' }}
            />
            <div style={{ textAlign: 'right', marginTop: '8px' }}>
              <button onClick={onCancelEdit} style={{ marginRight: '8px', padding: '4px 8px' }}>취소</button>
              <button onClick={handleUpdate} style={{ padding: '4px 8px' }}>저장</button>
            </div>
          </div>
        ) : (
          // --- 일반 모드 ---
          <div>
            <p style={{ margin: '4px 0 0', fontSize: '0.9rem', whiteSpace: 'pre-wrap' }}>{comment.content}</p>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <p style={{ margin: '8px 0 0', fontSize: '0.75rem', color: '#868e96' }}>{formatDate(comment.createdAt)}</p>
              {isAuthor && (
                <div>
                  <button onClick={onStartEdit} style={{ fontSize: '0.75rem', color: '#868e96', border: 'none', background: 'none', cursor: 'pointer' }}>수정</button>
                  <button onClick={() => onDelete(comment.id)} style={{ fontSize: '0.75rem', color: '#868e96', border: 'none', background: 'none', cursor: 'pointer', marginLeft: '8px' }}>삭제</button>
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};



export default function CommentSection({ postId }: { postId: number }) {
  const [comments, setComments] = useState<CommentResponse[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [currentPage, setCurrentPage] = useState<number>(0);
  const [totalPages, setTotalPages] = useState<number>(0);

  const [newComment, setNewComment] = useState('');
  const [editingCommentId, setEditingCommentId] = useState<number | null>(null);

  const loadComments = useCallback(async (pageToLoad: number) => {
    setIsLoading(true);
    try {
      const response: Page<CommentResponse> = await getCommentsByPost(postId, { page: pageToLoad, size: 5, sort: "createdAt,asc" });
      setComments(response.content);
      setTotalPages(response.totalPages);
      setCurrentPage(pageToLoad);
    } catch (error) {
      console.error("댓글을 불러오는 데 실패했습니다:", error);
      setComments([]);
      setTotalPages(0);
    } finally {
      setIsLoading(false);
    }
  }, [postId]);

  useEffect(() => {
    if (postId) {
      loadComments(0);
    }
  }, [postId, loadComments]);

  const handlePageChange = (page: number) => {
    loadComments(page);
  };

  const handleCreateComment = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newComment.trim()) return;

    try {
      await createComment(postId, { content: newComment });
      setNewComment('');
      loadComments(currentPage);
    } catch (error) {
      console.error("댓글 작성 실패:", error);
      alert("댓글 작성에 실패했습니다.");
    }
  };

  const handleDeleteComment = async (commentId: number) => {
    if (window.confirm("정말로 댓글을 삭제하시겠습니까?")) {
      try {
        await deleteComment(postId, commentId);
        // 댓글 삭제 후 현재 페이지를 다시 로드하여 목록을 갱신합니다.
        loadComments(currentPage);
      } catch (error) {
        console.error("댓글 삭제 실패:", error);
        alert("댓글 삭제에 실패했습니다.");
      }
    }
  };

  const handleUpdateComment = async (commentId: number, content: string) => {
    try {
      const updated = await updateComment(postId, commentId, { content });
      setComments(prev => prev.map(c => (c.id === commentId ? updated : c)));
      setEditingCommentId(null); // 수정 모드 종료
    } catch (error) {
      console.error("댓글 수정 실패:", error);
      alert("댓글 수정에 실패했습니다.");
    }
  };

  return (
    <div style={{ padding: '16px', paddingBottom: '120px' }}>
      <h4 style={{ margin: '0 0 16px' }}>댓글</h4>

      {isLoading ? (
        <div style={{ textAlign: 'center', padding: '20px' }}>댓글을 불러오는 중...</div>
      ) : comments.length > 0 ? (
        comments.map(comment => (
          <CommentItem
            key={comment.id}
            comment={comment}
            isEditing={editingCommentId === comment.id}
            onStartEdit={() => setEditingCommentId(comment.id)}
            onCancelEdit={() => setEditingCommentId(null)}
            onUpdate={handleUpdateComment}
            onDelete={handleDeleteComment}
          />
        ))
      ) : (
        <div style={{ textAlign: 'center', padding: '40px 20px', color: '#868e96' }}>
          작성된 댓글이 없습니다. <br />
          첫 댓글을 남겨보세요!
        </div>
      )}

      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={handlePageChange}
      />

      <form onSubmit={handleCreateComment} style={{
        position: 'fixed',
        bottom: '60px',
        left: 0,
        right: 0,
        maxWidth: '1000px',
        margin: '0 auto',
        padding: '8px 16px',
        borderTop: '1px solid #e0e0e0',
        backgroundColor: '#ffffff',
        display: 'flex',
        alignItems: 'center',
        boxSizing: 'border-box',
        zIndex: 100,
      }}>
        <input
          type="text"
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          placeholder="댓글을 입력하세요..."
          style={{
            flex: 1,
            padding: '10px',
            borderRadius: '20px',
            border: '1px solid #ddd',
            marginRight: '8px',
          }}
        />
        <button type="submit" style={{
          padding: '10px 16px',
          borderRadius: '20px',
          border: 'none',
          backgroundColor: '#ff6f0f',
          color: 'white',
          cursor: 'pointer',
        }}>
          등록
        </button>
      </form>
    </div>
  );
}
