package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

public record DummyIx(PairStatus pairStatus,
                      PairType pairType,
                      ActivationType activationType,
                      TokenProgramFlags tokenProgramFlag,
                      ResizeSide resizeSide,
                      Rounding rounding) implements Borsh {

  public static final int BYTES = 6;

  public static DummyIx read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var pairStatus = PairStatus.read(_data, i);
    i += Borsh.len(pairStatus);
    final var pairType = PairType.read(_data, i);
    i += Borsh.len(pairType);
    final var activationType = ActivationType.read(_data, i);
    i += Borsh.len(activationType);
    final var tokenProgramFlag = TokenProgramFlags.read(_data, i);
    i += Borsh.len(tokenProgramFlag);
    final var resizeSide = ResizeSide.read(_data, i);
    i += Borsh.len(resizeSide);
    final var rounding = Rounding.read(_data, i);
    return new DummyIx(pairStatus,
                       pairType,
                       activationType,
                       tokenProgramFlag,
                       resizeSide,
                       rounding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(pairStatus, _data, i);
    i += Borsh.write(pairType, _data, i);
    i += Borsh.write(activationType, _data, i);
    i += Borsh.write(tokenProgramFlag, _data, i);
    i += Borsh.write(resizeSide, _data, i);
    i += Borsh.write(rounding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
