package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record UpdateWeightMatrixParams(int collateralIndex,
                                       int[] weightMatrix,
                                       ExpectedLoanValues expectedLoanValues,
                                       byte[] assetIndexGuidance) implements Borsh {

  public static final int WEIGHT_MATRIX_LEN = 5;
  public static UpdateWeightMatrixParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var collateralIndex = _data[i] & 0xFF;
    ++i;
    final var weightMatrix = new int[5];
    i += Borsh.readArray(weightMatrix, _data, i);
    final var expectedLoanValues = ExpectedLoanValues.read(_data, i);
    i += Borsh.len(expectedLoanValues);
    final var assetIndexGuidance = Borsh.readbyteVector(_data, i);
    return new UpdateWeightMatrixParams(collateralIndex,
                                        weightMatrix,
                                        expectedLoanValues,
                                        assetIndexGuidance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) collateralIndex;
    ++i;
    i += Borsh.writeArray(weightMatrix, _data, i);
    i += Borsh.write(expectedLoanValues, _data, i);
    i += Borsh.writeVector(assetIndexGuidance, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 1 + Borsh.lenArray(weightMatrix) + Borsh.len(expectedLoanValues) + Borsh.lenVector(assetIndexGuidance);
  }
}
