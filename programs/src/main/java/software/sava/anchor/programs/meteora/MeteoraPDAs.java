package software.sava.anchor.programs.meteora;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.encoding.ByteUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;

public final class MeteoraPDAs {

  private static final byte[] BIN_ARRAY = "bin_array".getBytes(StandardCharsets.US_ASCII);
  private static final byte[] ORACLE = "oracle".getBytes(StandardCharsets.US_ASCII);
  private static final byte[] BIN_ARRAY_BITMAP_SEED = "bitmap".getBytes(StandardCharsets.US_ASCII);
  private static final byte[] PRESET_PARAMETER = "preset_parameter".getBytes(StandardCharsets.US_ASCII);
  private static final byte[] POSITION = "position".getBytes(StandardCharsets.US_ASCII);
  private static final byte[] ILM_BASE_KEY = PublicKey.fromBase58Encoded("MFGQxwAmB91SwuYX36okv2Qmdc9aMuHTwWGUrp4AtB1").toByteArray();

  public static ProgramDerivedAddress lbPairPDA(final PublicKey xMint,
                                                final PublicKey yMint,
                                                final int binStep,
                                                final int baseFactor,
                                                final PublicKey programId) {
    final PublicKey minKey;
    final PublicKey maxKey;
    if (xMint.compareTo(yMint) < 0) {
      minKey = xMint;
      maxKey = yMint;
    } else {
      minKey = yMint;
      maxKey = xMint;
    }

    final byte[] binStepBytes = new byte[Short.BYTES];
    ByteUtil.putInt16LE(binStepBytes, 0, binStep);
    final byte[] baseFactorBytes = new byte[Short.BYTES];
    ByteUtil.putInt16LE(baseFactorBytes, 0, baseFactor);

    return PublicKey.findProgramAddress(List.of(
            minKey.toByteArray(),
            maxKey.toByteArray(),
            binStepBytes,
            baseFactorBytes
        ), programId
    );
  }

  public static ProgramDerivedAddress customizablePermissionlessLbPairPDA(final PublicKey xMint,
                                                                          final PublicKey yMint,
                                                                          final PublicKey programId) {
    final PublicKey minKey;
    final PublicKey maxKey;
    if (xMint.compareTo(yMint) < 0) {
      minKey = xMint;
      maxKey = yMint;
    } else {
      minKey = yMint;
      maxKey = xMint;
    }

    return PublicKey.findProgramAddress(List.of(
            ILM_BASE_KEY,
            minKey.toByteArray(),
            maxKey.toByteArray()
        ), programId
    );
  }

  public static ProgramDerivedAddress permissionLbPairPDA(final PublicKey baseKey,
                                                          final PublicKey xMint,
                                                          final PublicKey yMint,
                                                          final int binStep,
                                                          final PublicKey programId) {
    final PublicKey minKey;
    final PublicKey maxKey;
    if (xMint.compareTo(yMint) < 0) {
      minKey = xMint;
      maxKey = yMint;
    } else {
      minKey = yMint;
      maxKey = xMint;
    }

    final byte[] binStepBytes = new byte[Short.BYTES];
    ByteUtil.putInt16LE(binStepBytes, 0, binStep);

    return PublicKey.findProgramAddress(List.of(
            baseKey.toByteArray(),
            minKey.toByteArray(),
            maxKey.toByteArray(),
            binStepBytes
        ), programId
    );
  }

  public static ProgramDerivedAddress positionPDA(final PublicKey lbPair,
                                                  final PublicKey baseKey,
                                                  final int lowerBinId,
                                                  final int width,
                                                  final PublicKey programId) {
    final byte[] lowerBinIdBytes = new byte[Integer.BYTES];
    ByteUtil.putInt32LE(lowerBinIdBytes, 0, lowerBinId);

    final byte[] widthBytes = new byte[Integer.BYTES];
    ByteUtil.putInt32LE(widthBytes, 0, width);

    return PublicKey.findProgramAddress(List.of(
            POSITION,
            lbPair.toByteArray(),
            baseKey.toByteArray(),
            lowerBinIdBytes,
            widthBytes
        ), programId
    );
  }

  public static ProgramDerivedAddress oraclePDA(final PublicKey lbPair, final PublicKey programId) {
    return PublicKey.findProgramAddress(List.of(
            ORACLE,
            lbPair.toByteArray()
        ), programId
    );
  }

  public static ProgramDerivedAddress binArrayPdA(final PublicKey lbPair,
                                                  final long binArrayIndex,
                                                  final PublicKey programId) {
    final byte[] binArrayIndexBytes = new byte[Long.BYTES];
    ByteUtil.putInt64LE(binArrayIndexBytes, 0, binArrayIndex);
    return PublicKey.findProgramAddress(List.of(
            BIN_ARRAY,
            lbPair.toByteArray(),
            binArrayIndexBytes
        ), programId
    );
  }

  public static ProgramDerivedAddress binArrayBitmapExtension(final PublicKey lbPair, final PublicKey programId) {
    return PublicKey.findProgramAddress(List.of(
            BIN_ARRAY_BITMAP_SEED,
            lbPair.toByteArray()
        ), programId
    );
  }

  public static ProgramDerivedAddress reservePDA(final PublicKey lbPair,
                                                 final PublicKey tokenMint,
                                                 final PublicKey programId) {
    return PublicKey.findProgramAddress(List.of(
            lbPair.toByteArray(),
            tokenMint.toByteArray()
        ), programId
    );
  }

  public static ProgramDerivedAddress rewardVaultPDA(final PublicKey lbPair,
                                                     final long rewardIndex,
                                                     final PublicKey programId) {
    final byte[] rewardIndexBytes = new byte[Long.BYTES];
    ByteUtil.putInt64LE(rewardIndexBytes, 0, rewardIndex);
    return PublicKey.findProgramAddress(List.of(
            lbPair.toByteArray(),
            rewardIndexBytes
        ), programId
    );
  }

  public static ProgramDerivedAddress presetParameterPDA(final int binStep,
                                                         final int baseFactor,
                                                         final PublicKey programId) {
    final byte[] binStepBytes = new byte[Short.BYTES];
    ByteUtil.putInt16LE(binStepBytes, 0, binStep);
    final byte[] baseFactorBytes = new byte[Short.BYTES];
    ByteUtil.putInt16LE(baseFactorBytes, 0, baseFactor);

    return PublicKey.findProgramAddress(List.of(
            PRESET_PARAMETER,
            binStepBytes,
            baseFactorBytes
        ), programId
    );
  }

  private MeteoraPDAs() {

  }
}
