package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.Borsh;

public record CandidateSwapResults(CandidateSwapResult[] results) implements Borsh {

  public static CandidateSwapResults read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var results = Borsh.readVector(CandidateSwapResult.class, CandidateSwapResult::read, _data, offset);
    return new CandidateSwapResults(results);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(results, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(results);
  }
}
