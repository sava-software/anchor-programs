package software.sava.anchor.programs.meteora.dlmm.anchor;

import java.math.BigInteger;

public final class LbClmmConstants {

  public static final int BASIS_POINT_MAX = 10000;

  public static final long MAX_BIN_PER_ARRAY = 70;

  public static final long MAX_BIN_PER_POSITION = 70;

  public static final int MIN_BIN_ID = -443636;

  public static final int MAX_BIN_ID = 443636;

  public static final long MAX_FEE_RATE = 100000000;

  public static final long FEE_PRECISION = 1000000000;

  public static final int MAX_PROTOCOL_SHARE = 2500;

  public static final int HOST_FEE_BPS = 2000;

  public static final long NUM_REWARDS = 2;

  public static final long MIN_REWARD_DURATION = 1;

  public static final long MAX_REWARD_DURATION = 31536000;

  public static final long EXTENSION_BINARRAY_BITMAP_SIZE = 12;

  public static final int BIN_ARRAY_BITMAP_SIZE = 512;

  public static final long MAX_REWARD_BIN_SPLIT = 15;

  public static final int MAX_BIN_STEP = 400;

  public static final BigInteger MAX_BASE_FEE = new BigInteger("100000000");

  public static final BigInteger MIN_BASE_FEE = new BigInteger("100000");

  public static final byte[] BIN_ARRAY = new byte[]{98, 105, 110, 95, 97, 114, 114, 97, 121};

  public static final byte[] ORACLE = new byte[]{111, 114, 97, 99, 108, 101};

  public static final byte[] BIN_ARRAY_BITMAP_SEED = new byte[]{98, 105, 116, 109, 97, 112};

  public static final byte[] PRESET_PARAMETER = new byte[]{112, 114, 101, 115, 101, 116, 95, 112, 97, 114, 97, 109, 101, 116, 101, 114};

  public static final byte[] POSITION = new byte[]{112, 111, 115, 105, 116, 105, 111, 110};

  private LbClmmConstants() {
  }
}
